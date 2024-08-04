const Login = {
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
  created() {
    const user = $cookies.get('user')
    if(user) {
      // this.$emit("authenticated", user);//$emit() function allows you to pass custom events up the component tree.
      // this.$router.replace({ name: "availability" });
    }
  },
  methods: {
    login() {
      this.msg = '';
      const headers = new Headers()
      headers.append('Content-Type', 'application/json')
      headers.append('Accept', '*/*')
      const requestOptions = {
        method: 'POST',
        mode: 'no-cors',
        headers: headers,
        body: JSON.stringify({
          password: this.input.password 
        }).replace(':', ': '),
      };
      fetch(BE + "/login?"
        + "username=" + this.input.username + '&'
        + "role=" + this.input.role,
        requestOptions
      )
      .then(response => {
        console.log(response)
        return response.text()
      }
      )
      .then( data =>{ 
        console.log(data)
        //This is the data you wanted to get from url
        if (data == null) {// didn't find this email password pair
          this.msg="Username or password incorrect.";
        } else if (data.message) {
          this.msg = data.message
        } else{
          // $cookies.set('user', data, '7d')
          // this.$emit("authenticated", data);//$emit() function allows you to pass custom events up the component tree.
          // this.$router.replace({ name: "availability" });
        }
      })
      .catch(error => {
        console.log(error)
        this.msg = "Error: "+error;
      });
    },
  },

  // define the template for the component
  template: `
	<div class="container center-content">
		<div class="w-50">
		<h2>Login</h2>
		<form @submit.prevent="login" id="loginForm">
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
			<button type="submit" class="btn btn-primary">Login</button>
		</form>
		<p class="mt-3">Don't have an account? <router-link to="/register">Register here</router-link></p>
		</div>
	</div>
  `
}
