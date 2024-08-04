const Admin = {
    props: {
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
        return {
            authenticatedUser: this.user,
            statistic: [],
            parkingArea: '',
            number: '',
            msg: ''
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
                BE + "/admin/" + this.user.userId + "/revenue-by-area?"
                + "areaIds=" + "A,B,C" + '&'
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
                    this.msg="Unable to load the statistic.";
                } else if (!data) {
                    this.msg = "Failed"
                } else {
                    this.statistic = data
                }
                })
                .catch(error => {
                this.msg = "Error: "+ error;
                });
        },
        addSlot() {
            this.msg = '';
            const requestOptions = {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                }
            };
            fetch(
                BE + "/admin/" + this.user.userId + "/add-parking-slot?"
                + "areaId=" + this.parkingArea + '&'
                + "slotNumber=" + this.number,
                requestOptions
            )
                .then( response =>response.json())
                .then( data =>{ 
                //This is the data you wanted to get from url
                if (data == null) {// didn't find this username password pair
                    this.msg="Unable to add parking slot."
                } else if (data?.message?.includes("Successfully")) {
                    this.msg = data.message
                } else {
                    this.msg="Unable to add parking slot."
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
    <div class="container full-height">
        <div class="row my-4">
            <div class="col-12">
            <h2>Admin Dashboard</h2>
            </div>
        </div>
        <h5 class="card-title">Statistic Report</h5>
        <div class="row mb-4">
            <!-- Dashboard Cards -->
            <div v-for="(s, is) in statistic" :key="is" class="col-md-4">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-header">Parking Area {{s.parkingArea}}</div>
                    <div class="card-body">
                        <h5 class="card-title" id="totalAreas">Total Reservation: {{s.totalReservation}}</h5>
                        <h5 class="card-title" id="totalAreas">Revenue: \${{s.revenue}}</h5>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-4">
            <!-- Add New Parking Spot Form -->
            <div class="col-md-12">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Add New Parking Spot</h5>
                        <form @submit.prevent="addSlot" id="addParkingSpotForm">
                            <div class="form-group">
                            <label for="spotArea">Area:</label>
                            <input type="text" class="form-control" id="area" v-model="parkingArea" placeholder="Enter area ID (A, B, C)" required>
                            </div>
                            <div class="form-group">
                            <label for="spotNumber">Spot Number:</label>
                            <input type="text" class="form-control" id="spotNumber" v-model="number" placeholder="Enter spot number" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Add Spot</button>
                        </form>
                        <p>{{msg}}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-4">
            <!-- Manage Existing Spots -->
            <div class="col-12">
            <div class="card">
                <div class="card-body">
                <h5 class="card-title">Manage Existing Spots</h5>
                <p>Future implementation: List of parking spots with options to edit or delete.</p>
                </div>
            </div>
            </div>
        </div>
    </div>
    `
}