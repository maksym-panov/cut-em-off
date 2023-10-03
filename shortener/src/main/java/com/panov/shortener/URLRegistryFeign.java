package com.panov.shortener;

import com.panov.shortener.dto.FullLinkRecord;
import com.panov.shortener.dto.LookupRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("url-registry")
public interface URLRegistryFeign {
    @PostMapping("/existing")
    FullLinkRecord getExistingURLMapping(@RequestBody LookupRequest request);

    @PostMapping
    void registerURLMapping(@RequestBody FullLinkRecord fullLinkRecord);
}
