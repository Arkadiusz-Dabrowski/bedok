package com.startup.bedok.advertisment.model.request;

import java.util.List;

public record DeleteAdvertisementPhotoRequest(
    List<String> photoIds){}
