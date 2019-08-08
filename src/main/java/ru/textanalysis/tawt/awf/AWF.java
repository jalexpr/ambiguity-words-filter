/*
 * Copyright (C) 2017  Alexander Porechny alex.porechny@mail.ru
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0) as published by the Creative Commons.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * for more details.
 *
 * You should have received a copy of the Attribution-NonCommercial-ShareAlike
 * 3.0 Unported (CC BY-SA 3.0) along with this program.
 * If not, see <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 * Thanks to Sergey Politsyn and Katherine Politsyn for their help in the development of the library.
 *
 *
 * Copyright (C) 2017 Александр Поречный alex.porechny@mail.ru
 *
 * Эта программа свободного ПО: Вы можете распространять и / или изменять ее
 * в соответствии с условиями Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0), опубликованными Creative Commons.
 *
 * Эта программа распространяется в надежде, что она будет полезна,
 * но БЕЗ КАКИХ-ЛИБО ГАРАНТИЙ; без подразумеваемой гарантии
 * КОММЕРЧЕСКАЯ ПРИГОДНОСТЬ ИЛИ ПРИГОДНОСТЬ ДЛЯ ОПРЕДЕЛЕННОЙ ЦЕЛИ.
 * См. Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * для более подробной информации.
 *
 * Вы должны были получить копию Attribution-NonCommercial-ShareAlike 3.0
 * Unported (CC BY-SA 3.0) вместе с этой программой.
 * Если нет, см. <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 * Благодарим Полицыных Сергея и Екатерину за оказание помощи в разработке библиотеки.
 */
package ru.textanalysis.tawt.awf;

import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.rfc.RelationshipHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class AWF {
    private RelationshipHandler relationshipHandler = new RelationshipHandler();

    /*
    возвращает true, если бы хотя бы одна связь была установлена
     */
    public boolean useAWFilterForBearingPhraseList(BearingPhraseSP bearingPhraseSP) {
        AtomicBoolean isCompatibility = new AtomicBoolean(false);
        bearingPhraseSP.applyConsumer(words -> {
            for(int i = 0; i < words.size(); i++) {
                for(int j = i + 1; j < words.size(); j++) {
                    if (relationshipHandler.establishRelation(j - i, words.get(i), words.get(j))) {
                        isCompatibility.set(true);
                    }
                }
            }
        });
        return isCompatibility.get();
    }
}
