package swin.hn.swe30003.osps.common

enum class UserRole(val role: String) {
    CUSTOMER("Customer"),
    ADMINI("Admin");

    override fun toString(): String {
        return role
    }
}