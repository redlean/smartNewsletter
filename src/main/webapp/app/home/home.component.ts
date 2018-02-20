import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {StateStorageService} from '../shared/auth/state-storage.service';
import {Router} from '@angular/router';
import { Account, LoginModalService, Principal } from '../shared';
import {LoginService} from "../shared/login/login.service";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    constructor(
        private principal: Principal,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private loginModalService: LoginModalService,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.credentials = {};
    }

    // ngOnInit() {
    //     this.principal.identity().then((account) => {
    //         this.account = account;
    //     });
    //     this.registerAuthenticationSuccess();
    // }
    //
    // registerAuthenticationSuccess() {
    //     this.eventManager.subscribe('authenticationSuccess', (message) => {
    //         this.principal.identity().then((account) => {
    //             this.account = account;
    //         });
    //     });
    // }
    //
    // isAuthenticated() {
    //     return this.principal.isAuthenticated();
    // }
    //
    // // login() {
    // //     this.modalRef = this.loginModalService.open();
    // // }
    // login() {
    //     this.loginService.login({
    //         username: this.username,
    //         password: this.password,
    //         rememberMe: this.rememberMe
    //     }).then(() => {
    //         this.authenticationError = false;
    //         //  this.activeModal.dismiss('login success');
    //         if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
    //             (/^\/reset\//.test(this.router.url))) {
    //             this.router.navigate(['']);
    //         }
    //
    //         this.eventManager.broadcast({
    //             name: 'authenticationSuccess',
    //             content: 'Sending Authentication Success'
    //         });
    //
    //         // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
    //         // // since login is succesful, go to stored previousState and clear previousState
    //         const redirect = this.stateStorageService.getUrl();
    //         if (redirect) {
    //             this.stateStorageService.storeUrl(null);
    //             this.router.navigate([redirect]);
    //         }
    //     }).catch(() => {
    //         this.authenticationError = true;
    //     });
    // }
    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
        //   this.activeModal.dismiss('cancel');
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    // login() {
    //     this.modalRef = this.loginModalService.open();
    // }
    login() {
        this.loginService.login({
            username: this.username,
            password: this.password,
            rememberMe: this.rememberMe
        }).then(() => {
            this.authenticationError = false;
            //  this.activeModal.dismiss('login success');
            if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
                (/^\/reset\//.test(this.router.url))) {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }
        }).catch(() => {
            this.authenticationError = true;
        });
    }

    register() {
        // this.activeModal.dismiss('to state register');
        this.router.navigate(['/register']);
    }

    requestResetPassword() {
        //   this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
    }
}
