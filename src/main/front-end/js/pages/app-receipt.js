const Receipt = {
    props: {
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
        return {
            authenticatedUser: this.user,
            receipts: []
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
                    this.msg="Unable to get receipts.";
                } else if (!data) {
                    this.msg = "Failed"
                } else {
                    this.receipts = data.reservations.filter(r => r.paidAt !== "null")
                    console.log(data.reservations)
                }
                })
                .catch(error => {
                this.msg = "Error: "+ error;
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
            <h2>Receipts</h2>
            <div id="gridView" class="row">
                <div v-for="(i,ii) in receipts" :key="ii" class="col-md-3 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Slot {{i.parkingArea}}{{i.parkingSlotNumber}}</h5>
                            <p class="card-text">Reserved at:<br/> {{displayTime(i.paidAt)}} </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
}