package com.greenism.msm.service;

import java.util.Map;

public interface MsmService {
    boolean send(String PhoneNumbers, String templateCode, Map<String,Object> map);
}
