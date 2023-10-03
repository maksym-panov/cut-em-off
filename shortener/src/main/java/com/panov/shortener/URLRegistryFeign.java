package com.panov.shortener;

import com.panov.urireg.FullLinkRecord;
import com.panov.urireg.LookupRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("URLRegistry")
public interface URLRegistryFeign {
    @PostMapping("/existing")
    FullLinkRecord getExistingURLMapping(@RequestBody LookupRequest request);

    @PostMapping
    void registerURLMapping(@RequestBody FullLinkRecord fullLinkRecord);
}
