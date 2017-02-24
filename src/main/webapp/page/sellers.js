app.controller('SellersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, sellersService) {

    $scope.page.current = 6;

    sellersService.list(function (response) {
        $scope.sellers = response.data;
    });

    $scope.addSeller = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createSeller.html',
            controller: 'CreateSellerController'
        }).finally(function () {
            sellersService.list(function (response) {
                $scope.sellers = response.data;
            });
        });
    };

});
