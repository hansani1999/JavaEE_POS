function saveItem(dto) {
    itemDB.push(dto);
}

function updateItem(dto) {
    for (var item of itemDB) {
        if (dto.getItemCode() == item.getItemCode()) {
            item.setItemCode(dto.getItemCode());
            item.setDescription(dto.getDescription());
            item.setPrice(dto.getPrice());
            item.setQty(dto.getQty());
        }
    }
}

function deleteItem(itemCode) {
    for (var item of itemDB) {
        if (item.getItemCode() == itemCode) {
            var index = itemDB.indexOf(item);
            itemDB.splice(index,1);
        }
    }
}

function searchItem(itemCode) {
    for (var item of itemDB) {
        if (item.getItemCode() == itemCode) {
            return item;
        }
    }
}

function bindItemTableEvents() {
    /*Get Item to Form*/
    $("#tblItem>tbody>tr").click(function () {
        let selectedRow = $(this);
        let itemCode = $(this).children(":nth-child(1)").text();
        let itemName = $(this).children(":nth-child(2)").text();
        let itemPrice = $(this).children(":nth-child(3)").text();
        let itemQty = $(this).children(":nth-child(4)").text();

        $("#itemCode").val(itemCode);
        $("#newItemName").val(itemName);
        $("#price").val(itemPrice);
        $("#quantity").val(itemQty);
    });

    /*Item Delete starts*/
    $("#tblItem>tbody>tr").dblclick(function () {
        let itemCode = $(this).children(":nth-child(1)").text();
        let itemName = $(this).children(":nth-child(2)").text();
        let itemPrice = $(this).children(":nth-child(3)").text();
        let itemQty = $(this).children(":nth-child(4)").text();

        $("#itemCode").val(itemCode);
        $("#newItemName").val(itemName);
        $("#price").val(itemPrice);
        $("#quantity").val(itemQty);

        let response = confirm("Are you sure you want to delete this Item?");
        if (response) {
            clearItemForm();
            deleteItem(itemCode);
            $(this).remove();
            loadItemIds();
        } else {

        }
    });
    /*Item Delete ends*/
}

function loadAllItems() {
    for (var item of itemDB) {
        let row = `<tr><td>${item.getItemCode()}</td><td>${item.getDescription()}</td><td>${item.getPrice()}</td><td>${item.getQty()}</td></tr>`;
        $("#tblItem").append(row);
    }
}

function getItemIds() {
    var itemIds = new Array();
    for (var item of itemDB) {
        itemIds.push(item.getItemCode());
    }
    return itemIds;
}

function updateQty(orderDetailList) {

}

function clearItemForm(){
    $("#itemCode").val("");
    $("#newItemName").val("");
    $("#price").val("");
    $("#quantity").val("");
}

function clearItemTable() {
    $("#tblItem>tbody>tr").empty();
}