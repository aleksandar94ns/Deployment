app.controller('HomeController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService, restaurantsService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;

    var loadData = function () {
        restaurantsService.listVisited(function(response) {
            $scope.restaurants = response.data;
        });
    };

    loadData();

    $scope.rate = function(reservation) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/rateRestaurant.html',
            controller: 'RateRestaurantController',
            locals: {
                reservation: reservation
            }
        }).finally(function () {
            loadData();
        });
    };
});
