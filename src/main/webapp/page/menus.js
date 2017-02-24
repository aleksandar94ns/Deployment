app.controller('MenusController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, menusService) {

    $scope.page.current = 10;

    menusService.list(function (response) {
        $scope.menus = response.data;
    });

    $scope.addMenu = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createMenu.html',
            controller: 'CreateMenuController'
        }).finally(function () {
            menusService.list(function (response) {
                $scope.menus = response.data;
            });
        });
    };
});