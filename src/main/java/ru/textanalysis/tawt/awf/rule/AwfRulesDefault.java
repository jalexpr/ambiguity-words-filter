package ru.textanalysis.tawt.awf.rule;

import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.rfc.RulesForCompatibility;
import ru.textanalysis.tawt.rfc.RulesForCompatibilityImpl;

public class AwfRulesDefault implements AwfRules {

	private RulesForCompatibility rulesForCompatibility;

	@Override
	public void init() {
		rulesForCompatibility = new RulesForCompatibilityImpl();
		rulesForCompatibility.init();
	}

	@Override
	public boolean establishRelation(Word main, Word dep) {
		return rulesForCompatibility.establishRelation(main, dep);
	}

	@Override
	public boolean establishRelation(int i, Word leftWord, Word rightWord) {
		return rulesForCompatibility.establishRelation(i, leftWord, rightWord);
	}

	@Override
	public boolean establishRelationForPretext(Word pretext, Word word) {
		return rulesForCompatibility.establishRelationForPretext(pretext, word);
	}
}
