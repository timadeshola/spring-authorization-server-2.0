package com.example.oauthserver.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/28/23
 * Time: 11:17 AM
 */
@Getter
@AllArgsConstructor
public enum AppStatus {
    ACTIVE('0'),
    INACTIVE('1');

    private Character status;
}
