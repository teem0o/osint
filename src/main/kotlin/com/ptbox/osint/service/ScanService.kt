package com.ptbox.osint.service


import com.ptbox.osint.dto.ScanMapper
import com.ptbox.osint.dto.ScanResponseDTO
import com.ptbox.osint.entity.Email
import com.ptbox.osint.entity.Host
import com.ptbox.osint.entity.Ip
import com.ptbox.osint.entity.Scan
import com.ptbox.osint.repository.ScanRepository
import org.springframework.stereotype.Service

@Service
class ScanService(scanRepository: ScanRepository, scanMapper: ScanMapper) {
    private val scanRepository: ScanRepository = scanRepository
    private val scanMapper: ScanMapper = scanMapper

    fun saveScanData(domain: String?, organizedData: Map<String?, List<String?>>) {
        val scan = Scan().apply { this.domain = domain }

        organizedData["Emails"]?.mapNotNull {
            it?.let { email ->
                Email().apply {
                    this.email = email; this.scan = scan
                }
            }
        }
            ?.let { scan.emails.addAll(it) }

        organizedData["IPs"]?.mapNotNull { it?.let { ip -> Ip().apply { this.ip = ip; this.scan = scan } } }
            ?.let { scan.ips.addAll(it) }

        organizedData["Hosts"]?.mapNotNull { it?.let { host -> Host().apply { this.host = host; this.scan = scan } } }
            ?.let { scan.hosts.addAll(it) }

        scanRepository.save(scan)
    }

    fun findByDomain(domain: String?): List<ScanResponseDTO> {
        val scans: List<Scan?> = scanRepository.findByDomain(domain) ?: emptyList()
        return scans.filterNotNull().map(scanMapper::toDTO)
    }
}