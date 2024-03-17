//
//  LoginScreen.swift
//  iosApp
//
//  Created by Sunil Kumar on 16/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import SwiftUI

struct LoginScreen: View {
    
    @ObservedObject var viewModel: LoginViewModel = LoginViewModel()
    
    @State private var showAlert: Bool = false
    
    //@State private var isProcessing: Bool = false
    
    
    var body: some View {
        VStack(spacing: 8.0) {
            
            Text("Login")
                .font(.title)
                .bold()
            
    
            TextField("Username", text: viewModel.binding(\.username))
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .disabled(viewModel.state(\.isProcessing))
            
            SecureField("Password", text: viewModel.binding(\.password))
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .disabled(viewModel.state(\.isProcessing))
            
            if viewModel.state(\.isProcessing) {
                ProgressView()
            } else {
                Button(action: {
                    viewModel.onButtonPressed()
                }, label: {
                    Text("Submit")
                        .foregroundStyle(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                })
            }
        }
        .padding()
        .onReceive(createPublisher(viewModel.actions), perform: { action in
            switch(action) {
            case is LoginViewModel.ActionLoginSuccess:
                //Navigate to HomeScreen
                showAlert = true
                break
            default:
                break
            }
        })
        .onAppear {
            
            viewModel.isProcessing.subscribe { state in
                //make some changes depending upon isProcessing
                if let isProcessingState = state {
                    //isProcessing = state as! Bool
                }
            }
            
        }.alert("Login Successful", isPresented: $showAlert) {
            Button("Close") {
                showAlert = false
            }
        }
    }
}
