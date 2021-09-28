package ru.textanalysis.tawt.awf;

import ru.textanalysis.tawt.ms.interfaces.InitializationModule;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;

public interface AmbiguityWordsFilter extends InitializationModule {

	/**
	 * Применить фильтр устранения неоднозначности на опорный оборот
	 *
	 * @param bearingPhrase - опорный оборот
	 */
	void applyAwfForBearingPhrase(BearingPhrase bearingPhrase);
}
