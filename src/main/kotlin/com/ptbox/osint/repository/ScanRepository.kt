package com.ptbox.osint.repository

import com.ptbox.osint.entity.Scan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScanRepository : JpaRepository<Scan?, Long?> {
    fun findByDomain(domain: String?): List<Scan?>?
}