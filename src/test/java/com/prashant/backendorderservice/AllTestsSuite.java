package com.prashant.backendorderservice;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.prashant.backendorderservice.orders",
        "com.prashant.backendorderservice.auth"
})
class AllTestsSuite {
}
