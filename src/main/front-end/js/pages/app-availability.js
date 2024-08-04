const Availability = {

    // define the template for the component
    template: `
    <div class="container center-content">
        <div class="w-75">
            <h2>Parking Slot Availability</h2>
            <div id="gridView" class="row">
            <!-- Row 1 -->
                <div v-for="i in [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22]" class="col-md-3 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Slot {{i}}</h5>
                            <p class="card-text">Available</p>
                            <button class="btn btn-success" data-toggle="modal" data-target="#registerSlotModal" data-slot="1">Register Slot</button>
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