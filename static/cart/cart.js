angular.module('market-front').controller('cartController', function ($scope, $location, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';

    $scope.loadCart = function () {
        $http({
            url: contextPath + '/cart/' + $localStorage.springWebGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
            // cartLengthStorage.setLength($scope.cart.items.length);
        });
    };



    $scope.clearCart = function () {
        $http.get(contextPath + '/cart/' + $localStorage.springWebGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.disableCheckOut = function () {
        alert("Для оформления заказа войдите в учётную запись.")
    };

    $scope.checkOut = function () {
        $http({
            url: contextPath + '/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null;
        });
    };

    $scope.loadCart();

});