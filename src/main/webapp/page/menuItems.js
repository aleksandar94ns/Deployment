app.controller('MenuItemsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, menuItemsService) {

    $scope.page.current = 8;

    menuItemsService.list(function (response) {
        $scope.menuItems = response.data;
    });

    $scope.addMenuItem = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createMenuItem.html',
            controller: 'CreateMenuItemController'
        }).finally(function () {
            menuItemsService.list(function (response) {
                $scope.menuItems = response.data;
            });
        });
    };
});