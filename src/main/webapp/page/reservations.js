app.controller('ReservationsController', function ($scope, $mdDialog, reservationsService, guestReservationsService) {

    $scope.page.current = 15;

    var loadData = function () {
        reservationsService.listMy(function (response) {
            $scope.pendingReservations = response.data.filter(function(reservation) {
                return reservation.status === 'PENDING';
            });
            $scope.acceptedReservations = response.data.filter(function(reservation) {
                reservation.cancelable = moment.duration(moment(Date.now()).diff(moment(reservation.reservation.arrivalDate))).asMinutes() < - 30;
                reservation.canOrder = moment.duration(moment(Date.now()).diff(moment(reservation.reservation.arrivalDate))).asMinutes() < 0;
                return reservation.status === 'ACCEPTED';
            });
        });
    };

    loadData();

    $scope.createOrder = function (reservation) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createOrder.html',
            controller: 'CreateOrderController',
            locals: {reservation: reservation}
        }).finally(function () {
            loadData();
        });
    };

    // Respond menu

    var originatorEv;

    $scope.openMenu = function($mdOpenMenu, ev) {
        originatorEv = ev;
        $mdOpenMenu(ev);
    };

    $scope.accept = function(reservation, ev) {
        reservation.status = 'ACCEPTED';
        reservationsService.update(reservation, function () {
            loadData();
        });
        originatorEv = null;
    };

    $scope.decline = function(reservation, ev) {
        reservation.status = 'DECLINED';
        reservationsService.update(reservation, function () {
            loadData();
        });
        originatorEv = null;
    };

    $scope.cancel = function (reservation, ev) {
        guestReservationsService.delete(reservation, function () {
            loadData();
        }, function () {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .title('Impossible!')
                    .content('You can cancel up to half an hour before the arrival time.')
                    .ok('Ok')
            );
        });
    };
});
