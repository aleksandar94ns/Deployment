app.controller('CreateBidController', function ($scope, $http, $state, $mdDialog, bidsService, supply, bid) {

    $scope.bid = bid;

    $scope.submit = function () {
        if (bid != null) {
            bidsService.patch($scope.bid, function () {
                $mdDialog.hide();
            });
        }
        else {
            $scope.bid.supply = supply;
            bidsService.create($scope.bid, function () {
                $mdDialog.hide();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});