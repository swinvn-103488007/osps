const Payment = {
    props: {
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
        return {
            authenticatedUser: this.user,
            bankAccount: ''
        }
    },
    methods: {
        updateBankAccount() {
            this.msg = '';
            const requestOptions = {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                }
            };
            fetch(
                BE + "/customer/" + this.user.userId + "/update-bank-account?"
                + "newValue=" + this.bankAccount,
                requestOptions
            )
                .then( response =>response.json())
                .then( data =>{ 
                //This is the data you wanted to get from url
                if (data == null) {// didn't find this username password pair
                    this.msg="Unable to update bank account."
                } else if (data?.message.includes("Successfully")) {
                    this.reservations.splice(pos,1)
                } else {
                    this.msg="Unable to update bank account."
                }
                })
                .catch(error => {
                    console.log(error)
                    this.msg = "Error: "+error;
                });
        },
    },
    mounted() {
        if(!this.user) {
            this.$router.replace({ name: "login" });
        }
    },
    // define the template for the component
    template: `
    <div class="container center-content">
        <div class="w-50">
            <h2>Update Bank Account</h2>
            <form @submit.prevent="updateBankAccount">
                <div class="form-group">
                    <label for="cardNumber">Card Number:</label>
                    <input type="text" class="form-control" v-model="bankAccount" id="cardNumber" placeholder="Enter card number">
                </div>
                <button type="submit" class="btn btn-primary">Update</button>
            </form>
        </div>
    </div>
    `
}