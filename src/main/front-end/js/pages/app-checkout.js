const Checkout = {
    props: {
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
        return {
            authenticatedUser: this.user,
            reservations: []
        }
    },
    methods: {
        loadData() {
            this.msg = '';
            const requestOptions = {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                }
            };
            fetch(
                BE + "/customer/" + this.user.userId + "/reservations?"
                + "from=" + "2024-01-01T17:01:40.977748700" + '&'
                + "to=" + "3000-01-01T17:01:40.977748700",
                requestOptions
            )
                .then( response =>{
                //turning the response into the usable data
                return response.json();
                })
                .then( data =>{ 
                //This is the data you wanted to get from url
                if (data == null) {// didn't find this username password pair
                    this.msg="Unable to load the reservations to checkout.";
                } else if (!data) {
                    this.msg = "Failed"
                } else {
                    this.reservations = data.reservations.filter(r => r.paidAt === "null" && r.checkoutAt === "null")
                }
                })
                .catch(error => {
                this.msg = "Error: "+ error;
                });
        },
        checkout(reservationId, pos) {
            this.msg = '';
            const requestOptions = {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                }
            };
            fetch(
                BE + "/customer/" + this.user.userId + "/checkout-reservation?"
                + "reservationId=" + reservationId,
                requestOptions
            )
                .then( response =>response.json())
                .then( data =>{ 
                //This is the data you wanted to get from url
                if (data == null) {// didn't find this username password pair
                    this.msg="Unable to make checkout."
                } else if (data?.message.includes("Successfully")) {
                    this.reservations.splice(pos,1)
                } else {
                    this.msg="Unable to checkout."
                }
                })
                .catch(error => {
                    console.log(error)
                    this.msg = "Error: "+error;
                });
        },
        displayTime(time) {
            return time.slice(0,10) + ' ' + time.slice(11,19)
        }
    },
    mounted() {
        if(this.user) {
          this.loadData()
        } else {
          this.$router.replace({ name: "login" });
        }
    },
    // define the template for the component
    template: `
    <div class="container center-content">
        <div class="w-75">
            <h2>Occupying Parking Slots</h2>
            <div id="gridView" class="row">
                <div v-for="(i,ii) in reservations" :key="ii" class="col-md-3 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Slot {{i.parkingArea}}{{i.parkingSlotNumber}}</h5>
                            <p class="card-text">Reserved at:<br/> {{displayTime(i.createdAt)}} </p>
                            <button class="btn btn-primary mb-2" @click="()=>this.checkout(i.id, ii)">Checkout</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
}