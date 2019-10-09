import React, {Component} from 'react';
import {Button, Form, Icon, Input, Layout} from "antd";
import './RegisterComponent.less';
import {serviceCheckUserName, serviceRegister} from "../services/auth/AuthService";
import loginLogo from "../img/bear-logo-grey.png";
import {openNotification} from "../common/notifications/AuthNotifications";
import {NavLink} from "react-router-dom";
import {PasswordInput} from "antd-password-input-strength";

const {Content} = Layout;

class RegisterForm extends Component {

  authToken;
  state = {
    checkingUserName: '',
    checkingUserEmail: false,
    checkingRegister: false,
  };

  //TODO - WALIDACJA
  validateAndSubmit = (e) => {
    this.setState({
      checkingRegister: true,
    });
    e.preventDefault();
    this.props.form.validateFields((error, credentials) => {
      if (!error) {
        serviceRegister(credentials).then(response => {
          if (response) {
            console.log(response);
            openNotification('registerSuccess');
            this.props.history.push('/login');
          }
        }).catch(authError => {
          console.log(authError);
          if (authError.status === 401) {
            openNotification('authError');
          } else {
            openNotification('serverAccess');
          }
          this.setState({
            checkingRegister: false,
          });
        })
      } else {
        console.log(error);
        this.setState({
          checkingRegister: false,
        });
      }
    })
  };

  compareToFirstPassword = (rule, value, callback) => {
    const {form} = this.props;
    if (value && value !== form.getFieldValue('userPassword')) {
      callback('Hasła nie pasują. Spróbuj ponownie.');
    } else {
      callback();
    }
  };

  checkUserName = (rule, value, callback) => {
    this.setState({
      checkingUserName: 'validating',
    });
    console.log(value);
    serviceCheckUserName(value).then(response => {
      console.log(response);
      if (response) {
        callback('Ta nazwa jest już zajęta');
      } else {
        callback();
      }
      this.setState({
        checkingUserName: 'success',
      });
    });

  };

  render() {

    const {getFieldDecorator} = this.props.form;

    return (
      <Layout>
        <Content className={"register-content"}>
          <div className={"register-header"}>
            <img src={loginLogo} alt="" className={"register-logo-icon"}/>
            Aplikacja
          </div>
          <Form
            onSubmit={this.validateAndSubmit}
            className={"register-form"}
          >
            <Form.Item
              hasFeedback
              validateStatus={this.state.checkingUserName}
              help={this.state.checkingUserName === '' ? "" : "Sprawdzam nazwę użytkownika"}
            >
              {getFieldDecorator('userName', {
                rules: [
                  {required: true, message: 'Podaj nazwę użytkownika.'},
                  {pattern: new RegExp("^\\S+$"), message: 'Podaj nazwę bez spacji.'},
                  {validator: this.checkUserName}
                ],
                validateTrigger: 'onBlur'
              })(
                <Input prefix={<Icon type={"user"}/>} className={'register-input'}
                       placeholder={"Nazwa użytkownika"} onFocus={this.handleFocus}/>
              )}
            </Form.Item>
            <Form.Item>
              {getFieldDecorator('userEmail', {
                rules: [
                  {required: true, message: 'Podaj email.'},
                  {type: 'email', message: 'Niepoprawny format email.'}
                ],
                validateTrigger: 'onBlur'
              })(
                <Input prefix={<Icon type={"mail"}/>} className={'register-input'}
                       placeholder={"Email"} onFocus={this.handleFocus}/>
              )}
            </Form.Item>
            <Form.Item>
              {getFieldDecorator('userPassword', {
                rules: [
                  {required: true, message: 'Podaj hasło.'},
                  {pattern: new RegExp("^\\S+$"), message: 'Podaj hasło bez spacji.'},
                ],
                validateTrigger: 'onBlur',
                settings: {
                  height: 4
                }
              })(
                <PasswordInput
                  settings={{
                    colorScheme: {
                      levels: ["#ff4033", "#fe940d", "#ffd908", "#cbe11d", "#6ecc3a"],
                      noLevel: "lightgrey"
                    },
                    height: 4,
                    alwaysVisible: false
                  }}
                  inputProps={{}}
                  prefix={<Icon type={"lock"}/>}
                  className={"register-input"}
                  type={"password"}
                  placeholder={"Hasło"}
                  onFocus={this.handleFocus}
                />
              )}
            </Form.Item>
            <Form.Item>
              {getFieldDecorator('userRepeatPassword', {
                rules: [
                  {required: true, message: 'Powtórz hasło.'},
                  {validator: this.compareToFirstPassword},
                ],
                validateTrigger: 'onBlur'
              })(
                <Input.Password
                  prefix={<Icon type={"lock"}/>}
                  className={"register-input"}
                  type={"password"}
                  placeholder={"Powtórz hasło"}
                  onFocus={this.handleFocus}
                />
              )}
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" className="register-form-button"
                      loading={this.state.checkingRegister}>
                <span className={'register-form-button-text'}>Zarejestruj się</span>
              </Button>
              Masz już konto?<NavLink to="/login"> <b>Zaloguj się.</b></NavLink>
            </Form.Item>
          </Form>
        </Content>
      </Layout>

    );
  }
}

export const RegisterComponent = Form.create({name: 'loginForm'})(RegisterForm);
