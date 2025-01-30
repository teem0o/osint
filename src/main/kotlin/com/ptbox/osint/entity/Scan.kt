package com.ptbox.osint.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


@Entity
@Table(name = "scans")
data class Scan (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var domain: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "scan", cascade = [CascadeType.ALL], orphanRemoval = true)
    val emails: MutableList<Email> = mutableListOf(),

    @OneToMany(mappedBy = "scan", cascade = [CascadeType.ALL], orphanRemoval = true)
    val ips: MutableList<Ip> = mutableListOf(),

    @OneToMany(mappedBy = "scan", cascade = [CascadeType.ALL], orphanRemoval = true)
    val hosts: MutableList<Host> = mutableListOf()
){
    fun addEmail(email: Email) {
        emails.add(email)
        email.scan = this
    }

    fun addIp(ip: Ip) {
        ips.add(ip)
        ip.scan = this
    }

    fun addHost(host: Host) {
        hosts.add(host)
        host.scan = this
    }
}
