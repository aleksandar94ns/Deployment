app.controller('SuppliesController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, suppliesService, authenticationService, bidsService) {

    $scope.page.current = 12;

    $scope.authService = authenticationService;

    $scope.seller = authenticationService.getUser();

    $scope.deleteBids = [];

    $scope.bids = [];

    var loadBids = function () {
        if ($scope.authService.isManager()) {
            bidsService.list(function (response) {

                $scope.bids = response.data;
                $scope.bids.forEach(function (bidz) {
                    if (bidz.status == "PENDING") {
                        $scope.deleteBids.push(bidz);
                    }
                });
                $scope.bids = [];
                $scope.deleteBids.forEach(function (bidzz) {
                    $scope.bids.push(bidzz);
                })
            })
        }
        else {
            bidsService.listBySeller($scope.seller.id, function (response) {
                $scope.bids = response.data;
            });
        }
    };

    loadBids();

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
            loadBids();
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
                loadBids();
            });
    };

    $scope.acceptBid = function(bid) {
        $scope.bid = bid;
        bidsService.put($scope.bid, function () {
            loadBids();
        })
    };

    $scope.isManager = function () {
        if (authenticationService.getUser().role == "MANAGER") {
            return true;
        }
    };

    $scope.isSeller = function () {
        if (authenticationService.getUser().role == "SELLER") {
            return true;
        }
    };

    $scope.bidStatus = function (bid) {
        if (authenticationService.getUser().role == "SELLER") {
            if (bid.status == "PENDING"){
                return true;
            }
        }
    };

    $scope.bidStatusManger = function (bid) {
        if (authenticationService.getUser().role == "MANAGER") {
            if (bid.status == "PENDING"){
                return true;
            }
        }
    };
});