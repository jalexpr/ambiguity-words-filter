package ru.textanalysis.tawt.awf.rule;

import ru.textanalysis.tawt.ms.model.sp.Word;

public interface AwfRules {

	void init();

	boolean establishRelation(Word main, Word dep);

	boolean establishRelation(int i, Word main, Word rightWord);

	boolean establishRelationForPretext(Word pretext, Word word);
}
