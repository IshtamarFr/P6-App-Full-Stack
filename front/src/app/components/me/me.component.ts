import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { Topic } from '../../features/articles/interfaces/topic.interface';
import { TopicService } from '../../features/articles/services/topic.service';
import {
  ValidatorFn,
  AbstractControl,
  ValidationErrors,
  Validators,
  FormBuilder,
  FormGroup,
} from '@angular/forms';
import { AuthSuccess } from 'src/app/features/auth/interfaces/authSuccess.interface';
import { RegisterRequest } from 'src/app/features/auth/interfaces/registerRequest.interface';
import { take } from 'rxjs';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public user: User | undefined;
  public topics$ = this.topicService.all();
  public userSubscriptions!: Array<Topic>;

  public hide = true;
  public onError = false;

  public form: FormGroup = this.fb.group({
    email: ['', [Validators.email, Validators.maxLength(63)]],
    name: ['', [Validators.minLength(3), Validators.maxLength(30)]],
    password: [
      '',
      [Validators.pattern('(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$)|null')],
    ],
  });

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private topicService: TopicService
  ) {}

  public ngOnInit(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe((user: User) => {
        this.user = user;
        this.userSubscriptions = user.subscriptions;

        this.form.setValue({
          email: this.user.email,
          name: this.user.name,
          password: '',
        });
      });
  }

  public back() {
    window.history.back();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  public unsubscribeTopic(topic: Topic): void {
    this.topicService.unsubscribeTopic(topic.id);
    this.userSubscriptions = this.userSubscriptions.filter(
      (e) => e.id !== topic.id
    );
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.update(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/articles']);
        });
      },
      error: (_) => {
        this.onError = true;
      },
    });
  }
}
