app.controller('SuppliesController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, suppliesService, authenticationService, bidsService) {

    $scope.page.current = 12;

    $scope.authService = authenticationService;

    $scope.seller = authenticationService.getUser();

    $scope.bids = [];

    $scope.supplies = [];

    $scope.acceptedBids = [];

    var loadBids = function () {
        $scope.bids = [];
        $scope.acceptedBids = [];
        if ($scope.authService.isManager()) {
            bidsService.list(function (response) {
                response.data.forEach(function (bidz) {
                    if (bidz.status == "PENDING") {
                        $scope.bids.push(bidz);
                    }
                    if (bidz.status == "ACCEPTED") {
                        $scope.acceptedBids.push(bidz);
                    }
                });
            })
        }
        else {
            bidsService.listBySeller($scope.seller.id, function (response) {
                $scope.bids = response.data;
            });
        }
    };

    loadBids();

    var loadSupplies = function () {
        $scope.supplies = [];
        suppliesService.list(function (response) {
            $scope.supplies = response.data;
        });
    };

    loadSupplies();

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
                loadBids();
            });
    };

    $scope.acceptBid = function(bid) {
        $scope.bid = bid;
        bidsService.put($scope.bid, function (response) {
            if(response.date == true){
                loadSupplies();
                loadBids();
            }
        });
        loadSupplies();
        loadBids();
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