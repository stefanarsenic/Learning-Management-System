import { Route } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { provideHttpClient, withInterceptors } from "@angular/common/http";

export const routes: Route[] = [
    {
        path: '',
        component: LoginComponent
    },
]