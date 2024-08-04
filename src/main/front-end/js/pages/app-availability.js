const Availability = {
    props: {
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
        return {
            authenticatedUser: this.user,
            selectingSlot: {
                areaId: '',
                number: ''
            },
            parkingAreas: []
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

            const fetchParkingSlots = ()=>{
                fetch(
                    BE + "/parking-map",
                    requestOptions
                )
                  .then( response =>{
                    //turning the response into the usable data
                    return response.json();
                  })
                  .then( data =>{ 
                    //This is the data you wanted to get from url
                    if (data == null) {// didn't find this username password pair
                        this.msg="Unable to load the parking slots.";
                    } else if (!data) {
                        this.msg = "Failed"
                    } else {
                        this.parkingAreas = data
                    }
                  })
                  .catch(error => {
                    this.msg = "Error: "+error;
                  });
            }

            fetchParkingSlots()
            setInterval(fetchParkingSlots, 30000)
        },
        reserve(areaId, number, pos) {
            this.msg = '';
            const requestOptions = {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                }
            };
            fetch(
                BE + "/customer/" + this.user.userId + "/reserve-parking-slot?"
                + "areaId=" + areaId + '&'
                + "slotNumber=" + number,
                requestOptions
            )
                .then( response =>response.json())
                .then( data =>{ 
                //This is the data you wanted to get from url
                if (data == null) {// didn't find this username password pair
                    this.msg="Unable to make reservation."
                } else if (data?.message.includes("Successfully") && pos?.length === 2) {
                    this.parkingAreas[pos[0]].slots[pos[1]].isAvailable = false
                } else {
                    this.msg="Unable to make reservation."
                }
                })
                .catch(error => {
                    console.log(error)
                    this.msg = "Error: "+error;
                });
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
            <h2>Parking Slot Availability</h2>
            <div v-for="(a,ia) in parkingAreas" :key="a.id">
                <h3 class="text-left">Parking Area: {{a.id}}</h3>
                <div id="gridView" class="row">
                    <div v-for="(p,ip) in a.slots" :key="p.number" class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Slot {{p.number}}</h5>
                                <p class="card-text">{{p.isAvailable ? "Available" : "Unavailable"}}</p>
                                <button :disabled="!p.isAvailable" class="btn" :class="{'btn-success': p.isAvailable, 'btn-danger': !p.isAvailable}" @click="()=>this.reserve(p.area, p.number, [ia,ip])">{{p.isAvailable ? "Reserve Slot" : "Reserved"}}</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Register Parking Slot Modal -->
        <div class="modal fade" id="registerSlotModal" tabindex="-1" role="dialog" aria-labelledby="registerSlotModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registerSlotModalLabel">Register Parking Slot</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                        <div class="form-group">
                            <label for="slotNumber">Slot Number:</label>
                            <input type="text" class="form-control" id="slotNumber" readonly>
                        </div>
                        <div class="form-group">
                            <label for="vehicleNumber">Vehicle Number:</label>
                            <input type="text" class="form-control" id="vehicleNumber" placeholder="Enter vehicle number">
                        </div>
                        <button type="submit" class="btn btn-primary">Proceed to Payment</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
}