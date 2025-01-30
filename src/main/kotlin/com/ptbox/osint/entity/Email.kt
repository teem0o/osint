package com.ptbox.osint.entity

import jakarta.persistence.*


@Entity
@Table(name = "emails")
class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "scan_id", nullable = false)
    var scan: Scan? = null

    @Column(nullable = false)
    var email: String? = null
}