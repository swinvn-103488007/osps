const Invoice = {

    // define the template for the component
    template: `
    <div class="container center-content full-height">
        <div class="w-75">
            <h2>Invoice</h2>
            <div class="card">
            <div class="card-body">
                <h5 class="card-title">Parking Invoice</h5>
                <p class="card-text">Date: <span id="invoiceDate">2024-08-02</span></p>
                <p class="card-text">Invoice Number: <span id="invoiceNumber">123456789</span></p>
                <p class="card-text">Vehicle: <span id="vehicleDetails">ABC1234</span></p>
                <table class="table">
                <thead>
                    <tr>
                    <th scope="col">Description</th>
                    <th scope="col">Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    <td>Parking Fee</td>
                    <td>$10.00</td>
                    </tr>
                    <tr>
                    <td>Service Charge</td>
                    <td>$2.00</td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                    <th>Total</th>
                    <th>$12.00</th>
                    </tr>
                </tfoot>
                </table>
                <button class="btn btn-primary" onclick="window.print()">Print Invoice</button>
            </div>
            </div>
        </div>
    </div>
    `
}