import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeComponent } from './components/me/me.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { TopicComponent } from './features/articles/components/topic/topic.component';

const routes: Routes = [
  {
    path: 'articles',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/articles/articles.module').then(
        (m) => m.ArticlesModule
      ),
  },
  { path: 'topics', canActivate: [AuthGuard], component: TopicComponent },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  { path: 'me', canActivate: [AuthGuard], component: MeComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
