app.controller('NavigationController', function ($scope, $state, $location, $log, $rootScope, $mdSidenav, $http, authenticationService) {

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

    $scope.goToHome = function () {
        $state.transitionTo('navigation.home');
        $mdSidenav('left').close();
    };
    $scope.goToRestaurantManagers = function() {
        $state.transitionTo('navigation.restaurantManagers');
        $mdSidenav('left').close();
    };

    $scope.goToSystemManagers = function() {
        $state.transitionTo('navigation.systemManagers');
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

    $scope.goToBartenders = function () {
        $state.transitionTo('navigation.bartenders');
        $mdSidenav('left').close();
    };

    $scope.goToWaiters = function () {
        $state.transitionTo('navigation.waiters');
        $mdSidenav('left').close();
    };

    $scope.goToChefs = function () {
        $state.transitionTo('navigation.chefs');
        $mdSidenav('left').close();
    };

    $scope.goToSellers = function () {
        $state.transitionTo('navigation.sellers');
        $mdSidenav('left').close();
    };

    $scope.goToMenus = function () {
        $state.transitionTo('navigation.menus');
        $mdSidenav('left').close();
    };

    $scope.goToMenuItems = function () {
        $state.transitionTo('navigation.menuItems');
        $mdSidenav('left').close();
    };

    $scope.goToDrinkCards = function () {
        $state.transitionTo('navigation.drinkCards');
        $mdSidenav('left').close();
    };

    $scope.goToDrinkItems = function () {
        $state.transitionTo('navigation.drinkItems');
        $mdSidenav('left').close();
    };
    //--------------------------------------
    $scope.goToWorkingPlace = function () {
        $state.transitionTo('navigation.workingPlace.workingHours');
        $mdSidenav('left').close();
    };
});