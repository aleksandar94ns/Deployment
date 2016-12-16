app.controller('NavigationController', function ($scope, $state, $location, $log, $rootScope, $mdSidenav, $http, $mdDialog, authenticationService) {

    $scope.page = {
        title: 'Restaurant',
        current: -1
    };

    $scope.user = authenticationService.getUser();
    $scope.authService = authenticationService;

    $scope.logout = function () {
        authenticationService.logout(function () {
            $state.transitionTo('login');
        }, function () {
        });
    };

    $scope.toggleSidenav = buildToggler('left');

    function buildToggler(navID) {
        return function () {
            $mdSidenav(navID).toggle();
        }
    }

    $scope.isActive = function(pageIndex) {
        return $scope.page.current === pageIndex;
    };

    $scope.addReservation = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createReservation.html',
            controller: 'CreateReservationController'
        });
    };

    $scope.goToHome = function () {
        $state.transitionTo('navigation.home');
        $mdSidenav('left').close();
    };

    $scope.goToRestaurants = function () {
        $state.transitionTo('navigation.restaurants');
        $mdSidenav('left').close();
    };

    $scope.goToProfile = function () {
        $state.transitionTo('navigation.profile');
        $mdSidenav('left').close();
    };
});