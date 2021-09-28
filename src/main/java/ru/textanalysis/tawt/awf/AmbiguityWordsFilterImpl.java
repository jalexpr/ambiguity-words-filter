package ru.textanalysis.tawt.awf;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.awf.rule.AwfRules;
import ru.textanalysis.tawt.awf.rule.AwfRulesDefault;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Word;

import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AmbiguityWordsFilterImpl implements AmbiguityWordsFilter {

	private AwfRules rules = new AwfRulesDefault();

	@Override
	public void init() {
		rules.init();
		log.debug("AWF is initialized!");
	}

	@Override
	public void applyAwfForBearingPhrase(BearingPhrase bearingPhrase) {
		bearingPhrase.applyConsumer(words -> {
			establishCompatibilityForPretext(words);
			establishCompatibilityForOneForm(words);
			establishCompatibilityForOneTypeOfSpeech(words);
		});
	}

	private void establishCompatibilityForPretext(List<Word> words) {
		int lastIndex = words.size() - 1;
		for (int i = lastIndex; -1 < i; i--) {
			Word word = words.get(i);
			if (word.havePretext()) {
				for (int j = lastIndex; i < j; j--) {
					rules.establishRelationForPretext(word, words.get(j));
				}
			}
		}
	}

	private void establishCompatibilityForOneForm(List<Word> words) {
		int lastIndex = words.size() - 1;
		for (int i = lastIndex; -1 < i; i--) {
			establishCompatibilityForOneForm(i, words);
		}
	}

	private void establishCompatibilityForOneForm(int i, List<Word> words) {
		int distance = 4; // todo попробовать учитывать расстояние
		int firstIndex = 0;
		int lastIndex = words.size() - 1;
		int leftNeighbor = i - 1;
		int rightNeighbor = i + 1;
		Word word = words.get(i);
		if (word.isOnlyOneForm()) {
			for (int j = Math.max(firstIndex, leftNeighbor); Math.max(firstIndex, leftNeighbor - distance) < j; j--) {
				Word left = words.get(j);
				if (!word.haveRelationship(left)) {
					if (rules.establishRelation(Math.abs(i - j), left, word)) {
						establishCompatibilityForOneForm(j, words);
					}
				}
			}
		}
		for (int j = Math.min(lastIndex, rightNeighbor); j < Math.min(lastIndex, rightNeighbor + distance); j++) {
			Word right = words.get(j);
			if (word.getMains().stream().noneMatch(main -> main.equals(right))) {
				if (rules.establishRelation(Math.abs(i - j), right, word)) {
					establishCompatibilityForOneForm(j, words);
				}
			}
		}
		if (word.haveContainsBearingForm()) {
			for (int j = Math.max(firstIndex, leftNeighbor); Math.max(firstIndex, leftNeighbor - distance) < j; j--) {
				Word left = words.get(j);
				if (!word.haveRelationship(left)) {
					if (rules.establishRelation(Math.abs(i - j), word, left)) {
						establishCompatibilityForOneForm(j, words);
					}
				}
			}
			for (int j = Math.min(lastIndex, rightNeighbor); j < Math.min(lastIndex, rightNeighbor + distance); j++) {
				Word right = words.get(j);
				if (!word.haveRelationship(right)) {
					if (rules.establishRelation(Math.abs(i - j), word, words.get(j))) {
						establishCompatibilityForOneForm(j, words);
					}
				}
			}
		}
	}

	private void establishCompatibilityForOneTypeOfSpeech(List<Word> words) {
		int lastIndex = words.size() - 1;
		for (int i = lastIndex; -1 < i; i--) {
			establishCompatibilityForOneTypeOfSpeech(i, words);
		}
	}

	private void establishCompatibilityForOneTypeOfSpeech(int i, List<Word> words) {
		int distance = 4;
		int firstIndex = 0;
		int lastIndex = words.size() - 1;
		int leftNeighbor = i - 1;
		int rightNeighbor = i + 1;
		Word word = words.get(i);
		if (word.isOnlyOneTypeOfSpeech() && word.haveContainsBearingForm()) {
			for (int j = Math.max(firstIndex, leftNeighbor); Math.max(firstIndex, leftNeighbor - distance) < j; j--) {
				Word left = words.get(j);
				if (!word.haveRelationship(left)) {
					if (rules.establishRelation(Math.abs(i - j), word, left)) {
						establishCompatibilityForOneTypeOfSpeech(j, words);
					}
				}
			}
			for (int j = Math.min(lastIndex, rightNeighbor); j < Math.min(lastIndex, rightNeighbor + distance); j++) {
				Word right = words.get(j);
				if (!word.haveRelationship(right)) {
					if (rules.establishRelation(Math.abs(i - j), word, right)) {
						establishCompatibilityForOneTypeOfSpeech(j, words);
					}
				}
			}
		}
	}
}
