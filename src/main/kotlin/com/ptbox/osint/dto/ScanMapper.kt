package com.ptbox.osint.dto

import com.ptbox.osint.entity.Scan
import org.springframework.stereotype.Component

@Component
class ScanMapper {
    fun toDTO(scan: Scan): ScanResponseDTO = ScanResponseDTO().apply {
        id = scan.id
        domain = scan.domain
        createdAt = scan.createdAt
        emails = scan.emails?.mapNotNull { it.email }.orEmpty()
        ips = scan.ips?.mapNotNull { it.ip }.orEmpty()
        hosts = scan.hosts?.mapNotNull { it.host }.orEmpty()
    }
}