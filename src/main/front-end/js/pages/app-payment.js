const Payment = {

    // define the template for the component
    template: `
    <div class="container center-content">
        <div class="w-50">
            <h2>Payment</h2>
            <form>
                <div class="form-group">
                    <label for="cardNumber">Card Number:</label>
                    <input type="text" class="form-control" id="cardNumber" placeholder="Enter card number">
                </div>
                <div class="form-group">
                    <label for="expiryDate">Expiry Date:</label>
                    <input type="text" class="form-control" id="expiryDate" placeholder="MM/YY">
                </div>
                <div class="form-group">
                    <label for="cvv">CVV:</label>
                    <input type="text" class="form-control" id="cvv" placeholder="Enter CVV">
                </div>
                <button type="submit" class="btn btn-primary">Make Payment</button>
            </form>
        </div>
    </div>
    `
}