const NavBar = {
    props: {
      user: {
          type: [Object, null],
          required: true
      }
    },
    data() {
        console.log(this.user)
      return {
          authenticatedUser: this.user
      }
    },
    template: `
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <router-link class="navbar-brand" to="/availability">Online Smart Parking Space</router-link>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                <li v-if="!user" class="nav-item">
                    <router-link class="nav-link" to="/">Login</router-link>
                </li>
                <li v-if="!user" class="nav-item">
                    <router-link class="nav-link" to="/register">Register</router-link>
                </li>
                <li v-if="user" class="nav-item">
                    <span class="nav-link">Welcome, {{user.name}}</span>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/availability">Parking Availability</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/checkout">Checkout</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/invoice">Invoice</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/receipt">Receipt</router-link>
                </li>
                <li v-if="user?.role && user.role === 'admin'" class="nav-item">
                    <router-link class="nav-link" to="/admin">Admin</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/payment">Payment</router-link>
                </li>
                <li v-if="user" class="nav-item">
                    <a class="nav-link" @click="()=>logout()">Log out</a>
                </li>
                </ul>
            </div>
        </nav>
    `,
      methods: {
        logout() {
            this.$root.logout();
        },
        activeNav(nav) {
            return {
                active: this.$route.name === nav
            }
        }
      },
      computed: {
          routeDescription() {
              return this.$route?.meta?.description
          }
      },
  }