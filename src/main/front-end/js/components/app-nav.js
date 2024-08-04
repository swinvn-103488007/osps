const NavBar = {
    props: {
      user: {
          type: [Object, null],
          required: true
      }
    },
    data() {
      return {
          authenticatedUser: this.user
      }
    },
    template: `
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <router-link class="navbar-brand" to="/availability">Parking System</router-link>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <router-link class="nav-link" to="/">Login</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/register">Register</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/availability">Parking Availability</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/invoice">Invoice</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/admin">Admin</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/payment">Payment</router-link>
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