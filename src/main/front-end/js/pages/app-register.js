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
  created() {
    const user = $cookies.get('user')
    if(user) {
      this.$emit("authenticated", user);//$emit() function allows you to pass custom events up the component tree.
      this.$router.replace({ name: "dashboard" });
    }
  },
  methods: {
    register() {
      this.msg = '';
      if (this.validate()) {
        const requestOptions = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              email: this.input.email.value,
              profile_name: this.input.username.value,
              password: this.input.password.value 
            })
        };
        fetch("resources/signup.php/", requestOptions)
          .then( response =>{
            //turning the response into the usable data
            return response.json( );
          })
          .then( data =>{ 
            //This is the data you wanted to get from url
            if (data == null) {// didn't find this username password pair
              this.msg="username or password incorrect.";
            } else if (data.message) {
              this.msg = data.message
            } else{
              $cookies.set('user', data.user, '7d')
              this.$emit("authenticated", data.user);//$emit() function allows you to pass custom events up the component tree.
              this.$router.replace({ name: "dashboard" });
            }
          })
          .catch(error => {
            this.msg = "Error: "+error;
          });
      }
    },
    reset() {
      Object.keys(this.input).forEach(i => {
        this.input[i].value = ''
        this.input[i].error = ''
      })
    },
    validate(name = null) {
      let valid = true
      for(const i in this.input) {
        if(!name || name === i) {
          for(const r in this.input[i].rules) {
            if(typeof this.input[i].rules[r] === 'function') {
              const result = this.input[i].rules[r](this.input[i].value)
              if(typeof result === 'string') {
                this.input[i].error = result
                valid = false
                break
              } else {
                this.input[i].error = ''
              }
            }
          }
        }
      }
      return valid
    },
  },

  // define the template for the component
  template: `
    <div class="container center-content">
      <div class="w-50">
        <h2>Register</h2>
        <form id="registerForm">
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
