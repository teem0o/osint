package com.ptbox.osint.entity

import jakarta.persistence.*


@Entity
@Table(name = "hosts")
class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "scan_id", nullable = false)
    var scan: Scan? = null

    @Column(nullable = false)
    var host: String? = null
}
