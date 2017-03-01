app.controller('StatisticsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, gradesService, reservationsService, guestReservationsService, orderItemsService) {

    $scope.page.current = 16;

    $scope.grades = [];
    $scope.reservations = [];
    $scope.guestReservations = [];
    $scope.totalGuests = 0;
    $scope.resGrade = 0;
    $scope.resGrades = 0;
    $scope.weiGrade = 0;
    $scope.weiGrades = 0;
    $scope.mealGrade = 0;
    $scope.mealGrades = 0;
    $scope.totalEarnings = 0;
    $scope.orderItems = [];
    $scope.days = [];
    $scope.bartendersAvarage = 0;
    $scope.orderItemsCount = 0;

    $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
    $scope.series = ['Series A', 'Series B'];
    $scope.data = [
        [0, $scope.totalGuests/4, $scope.totalGuests/2, ($scope.totalGuests/4)*3, $scope.totalGuests],
        [65, 59, 80, 81, 56]
    ];
    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
    $scope.options = {
        scales: {
            yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                {
                    id: 'y-axis-2',
                    type: 'linear',
                    display: true,
                    position: 'right'
                }
            ]
        }
    };

    var loadOrderItems = function () {
        $scope.orderItems = [];
        $scope.bartendersAvarage = 0;
        $scope.orderItemsCount = 0;
        orderItemsService.list(function (response) {
            $scope.orderItems = response.data;
            $scope.orderItems.forEach(function (orderItem) {
                $scope.totalEarnings += orderItem.menuItem.price;
                $scope.totalEarnings += orderItem.drinkItem.price;
                $scope.orderItemsCount++;
            });
        });
        $scope.bartendersAvarage = $scope.totalEarnings/$scope.orderItemsCount;
    };

    loadOrderItems();

    var loadGrades = function () {
        $scope.grades = [];
        $scope.resGrade = 0;
        $scope.resGrades = 0;
        $scope.weiGrade = 0;
        $scope.weiGrades = 0;
        $scope.mealGrade = 0;
        $scope.mealGrades = 0;
        $scope.guestReservations = [];
        $scope.totalGuests = 0;
        $scope.days = [];
        gradesService.list(function (response) {
            $scope.grades = response.data;
            $scope.grades.forEach(function (grade) {
                $scope.resGrades = $scope.resGrades + grade.restaurant;
                $scope.weiGrades = $scope.weiGrades + grade.waiter;
                $scope.mealGrades = $scope.mealGrades + grade.meal;
                $scope.restaurant = grade.reservation.restaurant.name;
                guestReservationsService.create(grade.reservation, function (response) {
                    $scope.guestReservations = response.data;
                    $scope.guestReservations.forEach(function (guest) {
                        $scope.totalGuests++;
                        $scope.days.push(guest.reservation.arrivalDate);
                    });
                })
            });
            $scope.resGrade = $scope.resGrades/$scope.grades.length;
            $scope.weiGrade = $scope.weiGrades/$scope.grades.length;
            $scope.mealGrade = $scope.mealGrades/$scope.grades.length;
        });
    };

    loadGrades();
});