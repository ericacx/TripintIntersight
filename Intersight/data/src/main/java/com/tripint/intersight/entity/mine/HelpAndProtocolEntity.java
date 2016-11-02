package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/11/2.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpAndProtocolEntity implements Serializable {

    private UserHelpEntity useApp;
    private UserProtocolEntity useAgree;

    public UserHelpEntity getUseApp() {
        return useApp;
    }

    public void setUseApp(UserHelpEntity useApp) {
        this.useApp = useApp;
    }

    public UserProtocolEntity getUseAgree() {
        return useAgree;
    }

    public void setUseAgree(UserProtocolEntity useAgree) {
        this.useAgree = useAgree;
    }
}
