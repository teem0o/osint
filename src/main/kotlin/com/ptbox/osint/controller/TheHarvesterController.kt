package com.ptbox.osint.controller


import com.ptbox.osint.dto.ScanResponseDTO
import com.ptbox.osint.service.ScanService
import com.ptbox.osint.service.TheHarvesterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/harvester")
class TheHarvesterController(harvesterService: TheHarvesterService, scanService: ScanService) {
    private val harvesterService: TheHarvesterService = harvesterService
    private val scanService: ScanService = scanService


    @GetMapping("/scan/{domain}")
    @Throws(Exception::class)
    fun getAllRecords(@PathVariable domain: String): Map<String?, MutableList<String?>> {
        return harvesterService.runTheHarvester(domain)
    }


    @GetMapping("/records/{domain}")
    fun getScanHistory(@PathVariable domain: String?): List<ScanResponseDTO> {
        return scanService.findByDomain(domain)
    }
}
