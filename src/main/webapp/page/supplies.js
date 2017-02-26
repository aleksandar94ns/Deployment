app.controller('SuppliesController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, suppliesService, authenticationService, bidsService) {

    $scope.page.current = 12;

    $scope.authService = authenticationService.getUser();

    $scope.seller = authenticationService.getUser();

    bidsService.listBySeller($scope.seller.id, function (response) {
        $scope.bids = response.data;
    });

    suppliesService.list(function (response) {
        $scope.supplies = response.data;
    });

    $scope.addSupply = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createSupply.html',
            controller: 'CreateSupplyController'
        }).finally(function () {
            suppliesService.list(function (response) {
                $scope.supplies = response.data;
            });
        });
    };

    $scope.addBid = function(supply) {
        $scope.supply = supply;
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createBid.html',
            controller: 'CreateBidController',
            locals: {
                supply: $scope.supply,
                bid : null
            }
        })
            .finally(function () {
            suppliesService.list(function (response) {
                $scope.supplies = response.data;
            });
        });
    };

    $scope.editBid = function(bid) {
        $scope.bid = bid;
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createBid.html',
            controller: 'CreateBidController',
            locals: {
                supply : null,
                bid: $scope.bid
            }
        })
            .finally(function () {
                suppliesService.list(function (response) {
                    $scope.supplies = response.data;
                });
            });
    };

    $scope.isManager = function () {
        if (authenticationService.getUser().role == "MANAGER") {
            return true;
        }
    }

    $scope.isSeller = function () {
        if (authenticationService.getUser().role == "SELLER") {
            return true;
        }
    }
});