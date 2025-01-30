package com.ptbox.osint.dto

import java.time.LocalDateTime


class ScanResponseDTO {
    var id: Long? = null
    var domain: String? = null
    var createdAt: LocalDateTime? = null
    var emails: List<String>? = null
    var ips: List<String>? = null
    var hosts: List<String>? = null
}