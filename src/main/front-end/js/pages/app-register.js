const Register = {
  // defining variables to be used in the component
  data() {
    return {
			msg:'',
      input: {
          username: '',
          password: '',
          role: 'customer'
      },
    }
  },
  methods: {
    register() {
      this.msg = '';
      const requestOptions = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            password: this.input.password 
          })
      };
      fetch(BE + "/register?"
        + "username=" + this.input.username + '&'
        + "role=" + this.input.role,
         requestOptions
        )
        .then( response =>{
          //turning the response into the usable data
          return response.json();
        })
        .then( data =>{ 
          //This is the data you wanted to get from url
          if (data == null) {// didn't find this username password pair
            this.msg="Unable to register.";
          } else if (!data.userId || !data.name) {
            this.msg = "Failed"
          } else {
            $cookies.set('user', {...data, role: this.input.role}, '7d')
            this.$emit("authenticated", {...data, role: this.input.role});//$emit() function allows you to pass custom events up the component tree.
            if(this.input.role === "customer") {
              this.$router.replace({ name: "availability" });
            } else {
              this.$router.replace({ name: "admin" });
            }
          }
        })
        .catch(error => {
          this.msg = "Error: "+error;
        });
    },
  },

  // define the template for the component
  template: `
    <div class="container center-content">
      <div class="w-50">
        <h2>Register</h2>
        <form @submit.prevent="register" id="registerForm">
          <div class="form-group">
            <label for="role">Role:</label>
            <select id="role" v-model="input.role" class="form-control" aria-label="Select role">
              <option selected value="customer">Customer</option>
              <option value="admin">Admin</option>
            </select>
          </div>
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" v-model="input.username" id="username" placeholder="Enter username" required>
          </div>
          <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" v-model="input.password" id="password" placeholder="Enter password" required>
          </div>
          <button type="submit" class="btn btn-primary">Register</button>
        </form>
      <p class="mt-3">Already have an account? <router-link to="/">Login here</router-link></p>
      </div>
    </div>
  `
}
