const Admin = {

    // define the template for the component
    template: `
    <div class="container full-height">
        <div class="row my-4">
            <div class="col-12">
            <h2>Admin Dashboard</h2>
            </div>
        </div>
        <div class="row mb-4">
            <!-- Dashboard Cards -->
            <div class="col-md-4">
            <div class="card text-white bg-primary mb-3">
                <div class="card-header">Total Parking Areas</div>
                <div class="card-body">
                <h5 class="card-title" id="totalAreas">5</h5>
                </div>
            </div>
            </div>
            <div class="col-md-4">
            <div class="card text-white bg-success mb-3">
                <div class="card-header">Total Parking Spots</div>
                <div class="card-body">
                <h5 class="card-title" id="totalSpots">50</h5>
                </div>
            </div>
            </div>
            <div class="col-md-4">
            <div class="card text-white bg-warning mb-3">
                <div class="card-header">Occupied Spots</div>
                <div class="card-body">
                <h5 class="card-title" id="occupiedSpots">20</h5>
                </div>
            </div>
            </div>
        </div>
        <div class="row mb-4">
            <!-- Add New Parking Area Form -->
            <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-body">
                <h5 class="card-title">Add New Parking Area</h5>
                <form id="addParkingAreaForm">
                    <div class="form-group">
                    <label for="areaName">Area Name:</label>
                    <input type="text" class="form-control" id="areaName" placeholder="Enter area name" required>
                    </div>
                    <div class="form-group">
                    <label for="areaLocation">Location:</label>
                    <input type="text" class="form-control" id="areaLocation" placeholder="Enter location" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Area</button>
                </form>
                </div>
            </div>
            </div>
            <!-- Add New Parking Spot Form -->
            <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-body">
                <h5 class="card-title">Add New Parking Spot</h5>
                <form id="addParkingSpotForm">
                    <div class="form-group">
                    <label for="spotArea">Select Area:</label>
                    <select class="form-control" id="spotArea" required>
                        <!-- Options will be populated dynamically -->
                        <option value="">Choose an area</option>
                        <option value="Area1">Area1</option>
                        <option value="Area2">Area2</option>
                    </select>
                    </div>
                    <div class="form-group">
                    <label for="spotNumber">Spot Number:</label>
                    <input type="text" class="form-control" id="spotNumber" placeholder="Enter spot number" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Spot</button>
                </form>
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