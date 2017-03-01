/**
 * Created by Alex on 10/29/16.
 */
'use strict';

var app = angular.module('app', ['ui.router', 'ngMessages', 'ngMaterial', 'material.svgAssetsCache', 'mdPickers', 'materialCalendar', 'ngMap']);

app.factory('authInterceptor', ['$q', '$injector', function ($q, $injector) {
    var authInterceptor = {
        responseError: function (response) {
            var $state = $injector.get('$state');
            if ($state.current.name !== 'login' && response.status == 401) {
                $state.transitionTo('login');
            }
            return $q.reject(response);
        }
    };
    return authInterceptor;
}]);

app.config(function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider, $mdDateLocaleProvider) {

    $mdDateLocaleProvider.formatDate = function(date) {
        return moment(date).format('DD-MM-YYYY');
    };

    $mdDateLocaleProvider.parseDate = function(dateString) {
        var m = moment(dateString, 'DD-MM-YYYY', true);
        return m.isValid() ? m.toDate() : new Date(NaN);
    };

    $mdThemingProvider.theme('default')
        .accentPalette('orange');

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('authInterceptor');

    $stateProvider
        .state('login', {
            url: '/login',
            controller: 'LoginController',
            templateUrl: 'page/login.html'
        })
        .state('navigation', {
            url: '',
            abstract: true,
            controller: 'NavigationController',
            templateUrl: 'page/navigation.html'
        })
        .state('navigation.reservations', {
            url: '/reservations',
            controller: 'ReservationsController',
            templateUrl: 'page/reservations.html'
        })
        .state('navigation.home', {
            url: '/home',
            controller: 'HomeController',
            templateUrl: 'page/home.html'
        })
        .state('navigation.restaurantManagers', {
            url: '/restaurantManagers',
            controller: 'RestaurantManagersController',
            templateUrl: 'page/restaurantManagers.html'
        })
        .state('navigation.systemManagers', {
            url: '/systemManagers',
            controller: 'SystemManagersController',
            templateUrl: 'page/systemManagers.html'
        })
        .state('navigation.restaurantTypes', {
            url: '/restaurantTypes',
            controller: 'RestaurantTypesController',
            templateUrl: 'page/restaurantTypes.html'
        })
        .state('navigation.restaurants', {
            url: '/restaurants',
            controller: 'RestaurantsController',
            templateUrl: 'page/restaurants.html'
        })
        .state('navigation.employees', {
            url: '/employees',
            controller: 'EmployeesController',
            templateUrl: 'page/employees.html'
        })
        .state('navigation.sellers', {
            url: '/sellers',
            controller: 'SellersController',
            templateUrl: 'page/sellers.html'
        })
        .state('navigation.supplies', {
            url: '/supplies',
            controller: 'SuppliesController',
            templateUrl: 'page/supplies.html'
        })
        .state('navigation.areas', {
            url: '/areas',
            controller: 'AreasController',
            templateUrl: 'page/areas.html'
        })
        .state('navigation.drinkCards', {
            url: '/drinkCards',
            controller: 'DrinkCardsController',
            templateUrl: 'page/drinkCards.html'
        })
        .state('navigation.drinkItems', {
            url: '/drinkItems',
            controller: 'DrinkItemsController',
            templateUrl: 'page/drinkItems.html'
        })
        .state('navigation.menus', {
            url: '/menus',
            controller: 'MenusController',
            templateUrl: 'page/menus.html'
        })
        .state('navigation.menuItems', {
            url: '/menuItems',
            controller: 'MenuItemsController',
            templateUrl: 'page/menuItems.html'
        })
        .state('navigation.profile', {
            url: '/profile',
            controller: 'ProfileController',
            templateUrl: 'page/profile.html'
        })
        .state('navigation.workingPlace', {
            url: '/workingPlace',
            controller: 'WorkingPlaceController',
            templateUrl: 'page/workingPlace.html'
        })
        .state('navigation.workingPlace.workingArea', {
            url: '/workingArea',
            controller: 'WorkingAreaController',
            templateUrl: 'page/workingArea.html'
        })
        .state('navigation.workingPlace.workingHours', {
            url: '/workingHourse',
            controller: 'WorkingHoursController',
            templateUrl: 'page/workingHours.html'
        })
        .state('navigation.workingPlace.drinks', {
            url: '/drinks',
            controller: 'DrinksController',
            templateUrl: 'page/drinks.html'
        })
        .state('navigation.workingPlace.salads', {
            url: '/salads',
            controller: 'SaladsController',
            templateUrl: 'page/salads.html'
        })
        .state('navigation.workingPlace.cookedMeals', {
            url: '/cookedMeals',
            controller: 'CookedMealsController',
            templateUrl: 'page/cookedMeals.html'
        })
        .state('navigation.statistics', {
            url: '/statistics',
            controller: 'StatisticsController',
            templateUrl: 'page/statistics.html'
        })
        .state('navigation.shifts', {
            url: '/shifts',
            controller: 'ShiftsController',
            templateUrl: 'page/shifts.html'
        })
        .state('navigation.changePasswordSeller', {
            url: '/changePasswordSeller',
            controller: 'ChangePasswordSellerController',
            templateUrl: 'page/changePasswordSeller.html'
        })
        .state('navigation.workingPlace.grillMeals', {
            url: '/grillMeals',
            controller: 'GrillMealsController',
            templateUrl: 'page/grillMeals.html'
        });
});